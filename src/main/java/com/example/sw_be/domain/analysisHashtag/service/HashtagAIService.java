package com.example.sw_be.domain.analysisHashtag.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagAIService {

    @Qualifier("openAiWebClient")
    private final WebClient openAiWebClient;

    private final ObjectMapper om = new ObjectMapper();

    public List<String> generateHashTags(String content) {
        String rid = UUID.randomUUID().toString().substring(0, 8);

        String prompt = """
            You are a concise hashtag generator for Korean movie analyses.
            Output MUST be valid JSON only: {"hashtags":["#태그1","#태그2","#태그3"]}
            Rules:
            - Exactly 3 hashtags
            - Korean preferred
            - 4~10 chars each
            - No spaces, must start with '#'
            - No duplicates, avoid generic words
            Content:
            """ + (content == null ? "" : content);

        Map<String, Object> schema = Map.of(
                "$schema", "http://json-schema.org/draft-07/schema#",
                "type", "object",
                "additionalProperties", false,
                "required", List.of("hashtags"),
                "properties", Map.of(
                        "hashtags", Map.of(
                                "type", "array",
                                "minItems", 3,
                                "maxItems", 3,
                                "items", Map.of("type", "string")
                        )
                )
        );

        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "input", prompt,
                "max_output_tokens", 200,
                "text", Map.of(
                        "format", Map.of(
                                "type", "json_schema",
                                "name", "hashtags_format",
                                "strict", true,
                                "schema", schema
                        )
                )
        );

        try {
            ResponseEntity<String> entity = openAiWebClient.post()
                    .uri("/responses")
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, resp ->
                            resp.bodyToMono(String.class).flatMap(err ->
                                    Mono.error(new RuntimeException("OpenAI error " + resp.statusCode() + ": " + err))
                            )
                    )
                    .toEntity(String.class)
                    .timeout(Duration.ofSeconds(8))
                    .block();

            String resp = entity != null ? entity.getBody() : null;
            String text = extractText(resp);

            List<String> tags = parseHashtagsJson(text);
            if (tags.isEmpty()) tags = parseBracketList(text);
            if (tags.isEmpty()) tags = extractByRegex(text);

            if (tags.isEmpty()) {
                return fallback(rid, "empty parsed");
            }

            List<String> norm = normalize(tags);
            log.info("[HTAG {}] hashtags={}", rid, norm);
            return norm;

        } catch (Exception e) {
            log.error("[HTAG {}] error: {}", rid, e.getMessage());
            return fallback(rid, "exception");
        }
    }

    private List<String> fallback(String rid, String reason) {
        log.warn("[HTAG {}] fallback({})", rid, reason);
        return List.of("#영화해석", "#감정여운", "#주제분석");
    }

    // Responses API: output_text 우선, 없으면 호환 경로 + 코드펜스 제거
    private String extractText(String respJson) {
        try {
            JsonNode root = om.readTree(respJson == null ? "{}" : respJson);

            String ot = root.path("output_text").asText(null);
            if (ot != null && !ot.isBlank()) return stripCodeFences(ot);

            JsonNode output = root.path("output");
            if (output.isArray()) {
                StringBuilder sb = new StringBuilder();
                for (JsonNode item : output) {
                    if ("message".equals(item.path("type").asText(""))) {
                        JsonNode contentArr = item.path("content");
                        if (contentArr.isArray()) {
                            for (JsonNode c : contentArr) {
                                String t = c.path("text").asText(null);
                                if (t != null) sb.append(t);
                            }
                        }
                    }
                }
                if (sb.length() > 0) return stripCodeFences(sb.toString());
            }

            JsonNode choices = root.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                String t = choices.get(0).path("message").path("content").asText(null);
                if (t != null && !t.isBlank()) return stripCodeFences(t);
            }

            return respJson;
        } catch (Exception e) {
            return respJson;
        }
    }

    private String stripCodeFences(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.startsWith("```")) {
            int first = t.indexOf('\n');
            int last = t.lastIndexOf("```");
            if (first >= 0 && last > first) return t.substring(first + 1, last).trim();
        }
        return t;
    }

    private List<String> parseHashtagsJson(String text) {
        try {
            if (text == null) return List.of();
            int s = text.indexOf('{'), e = text.lastIndexOf('}');
            if (s >= 0 && e > s) {
                JsonNode node = om.readTree(text.substring(s, e + 1));
                JsonNode arr = node.path("hashtags");
                if (arr.isArray() && arr.size() == 3) {
                    List<String> out = new ArrayList<>(3);
                    arr.forEach(n -> out.add(n.asText()));
                    return out;
                }
            }
        } catch (Exception ignored) {}
        return List.of();
    }

    private List<String> parseBracketList(String text) {
        if (text == null) return List.of();
        String trimmed = text.trim();
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            trimmed = trimmed.substring(1, trimmed.length() - 1);
            String[] parts = trimmed.split("\\s*,\\s*");
            List<String> out = new ArrayList<>();
            for (String p : parts) {
                String t = p.replaceAll("^[\"']|[\"']$", "").trim();
                if (!t.isBlank()) out.add(t);
            }
            return out;
        }
        return List.of();
    }

    private List<String> extractByRegex(String text) {
        if (text == null) return List.of();
        var m = Pattern.compile("#[\\p{L}\\p{N}_]+").matcher(text);
        List<String> out = new ArrayList<>();
        while (m.find() && out.size() < 3) out.add(m.group());
        return out;
    }

    private List<String> normalize(List<String> tags) {
        return tags.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(s -> s.startsWith("#") ? s : "#" + s)
                .map(s -> s.replaceAll("\\s+", ""))
                .distinct()
                .limit(3)
                .toList();
    }
}
