package com.example.hello.controller;

import com.example.hello.dto.RequestLogin;
import com.example.hello.dto.ResponseUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Requset:
 * POST /api/user/ http 1.1    ----> Method Path    현재 사용하는 HTTP VER
 * Host: api.example.com        ---> host address
 * Content-type: Application/Json ->
 * <공백줄>
 * {                        ---->Request Body
 *     name="홍길동"
 * }
 *
 * Response:
 * Http/ 1.1 200 Ok        ----->Http Ver, 응답코드(Status Code)
 * Content-type: Application/Json ->
 * ...
 * <공백줄>
 * {
 *
 * }
 *
 * Http Method:
 * GET: 조회
 * POST: 생성 또는 처리
 * PUT:생성
 * PATCH:수정
 * DELETE:삭제
 * HEAD:헤더만 조회
 * OPTIONS: 어떠한 메소드를 지원하는가?
 *
 * 응답코드:
 * 100번대:정보
 * 200번대:성공
 * 300번대: 리다이렉트(다른 페이지로 보냄)
 * 400번대:client error
 * 500번대: Server error
 *
 * 200: ok
 * 400: Bad Request, 요청형식이 잘 못됨
 * 401: Unauthorized, 인증안됨
 * 403: Forbidden, 인가안됨
 * 404: Not Found, 해당 페이지 없음
 * 405: Method not allowed, 해당 메소드를 지원하지 않음
 * 500: Internal Server Error,
 * */

//인증 : Authentication
//인가 : Authorization


@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "World")String name){
        return "Hello, " + name + "!";
    }

    /**
     * path:login
     * login()함수를 정의하세요
     * Method: Get
     * parameter1: String id
     * parameter2: String password
     *
     * id==test@example.com && password == "1234" 이면 "OK"를 반환
     * 아니면 "FAILED" 를 반환하세요
     *
     *
     *
     * */
    @GetMapping("/login")
    public String login(@RequestParam(defaultValue="")String id,
                        @RequestParam(defaultValue="")String password){
        if(id.equals("test@example.com") && password.equals("1234"))
            return "OK";

        return "FAILED";
    }

    @PostMapping("/login")
    public String signin(@RequestBody RequestLogin login){
               if(login.getId().equals("test@example.com") && login.getPassword().equals("1234"))
                   return "OK";

        return "FAILED";
    }

    @GetMapping("/user/{id}") //localhost:8090/user/1
    public ResponseEntity<ResponseUser> getUser(@PathVariable Long id){
        //test로 id가 1이면 성공 아니면 실패
        //성공하면 사용자 이름, 이메일을 반환
        if(id==null || id <=0){
            //client의 request가 잘못된 값일때 badRequest를 반환
            return ResponseEntity.badRequest().build();
        }

        if(id==1){
            ResponseUser user = new ResponseUser("1" , "홍길동", "test@example.com");
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        //404에러 <==id를 못찾은 경우
        return ResponseEntity.notFound().build();
    }
}
//curl -i -X POST "http://localhost:8090/login" -H "Content-type: application/json" -d "{\"id\":\"test@example.com\", \"password\":\"1234\"}"