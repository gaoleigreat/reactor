package com.example.refectorpgsql;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lay
 * @description
 * @date 2023年04月01日 11:15
 */
@RestController
public class TestController {
    private static void accept(Throwable e) {
        return ;
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity> test(@PathVariable("id") Integer id) {
        Map<String, String> map = new HashMap<>();
        if (id == 1) {
            map.put("error", "params error");
            ResponseEntity responseEntity = new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            return Mono.just(responseEntity);
        } else {
            map.put("success", "success");
            ResponseEntity responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
            return Mono.just(responseEntity);
        }
    }

    @GetMapping("test")
    public Mono<Map> test() {
        WebClient webClient = WebClient.builder().baseUrl("http://127.0.0.1:8080").build();
        return webClient.get().uri("/1").exchangeToMono(response -> {
            Map<String, Object> map = new HashMap<>();
            HttpStatusCode httpStatusCode = response.statusCode();
            return response.bodyToMono(Map.class).map(s-> {
                map.put("carmHttpStatus",httpStatusCode.value());
                map.put("carmResponseBody",s);
                return map;
            });
        });

    }
}
