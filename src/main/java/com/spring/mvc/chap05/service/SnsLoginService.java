package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.dto.response.KaKaoUserResponseDTO;
import com.spring.mvc.chap05.entity.LoginMethode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class SnsLoginService {
    private final MemberSerivce memberSerivce;
    /** 카카오 로그인 처리 */
    public void kakaoLogin(Map<String,String> reqParam, HttpSession session){
//        인가 코드를 가지고 토큰을 발급바든 요청 보내기
        String kakaoAccessToken = getKakaoAccessToken(reqParam);
        log.debug("access_token : {}",kakaoAccessToken);

        /* 토큰을 통해서 사용자 정보 가져오기 */
        KaKaoUserResponseDTO kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken);
        String nickname = kakaoUserInfo.getProperties().getNickname();

        // 회원 중복확인-  원래는 이메일로 검사 해야함,
        if (!memberSerivce.checkDuplicateValue("account",nickname)){
            //받은 회원정보로 우리 사이트 회원가입 처리 하기
            memberSerivce.join(
                    SignUpRequestDTO.builder()
                            .account(nickname)
                            .password("0000")
                            .name(nickname)
                            .email(nickname+"@ac.c")
                            .loginMethode(LoginMethode.KAKAO)
                            .build(),
                    kakaoUserInfo.getProperties().getProfileImage()
            );
        }
        // 우리 사이트 로그인 처리
        memberSerivce.maintainLoginState(session, nickname);


    }

    private KaKaoUserResponseDTO getKakaoUserInfo(String kakaoAccessToken) {
        String reqUri = "https://kapi.kakao.com/v2/user/me";

        //요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+kakaoAccessToken);
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        // 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KaKaoUserResponseDTO> responseEntity = restTemplate.exchange(
                reqUri,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                KaKaoUserResponseDTO.class
        );

        //응답 정보 꺼내기
        KaKaoUserResponseDTO responseJSON = responseEntity.getBody();
        log.debug("user profile : {}",responseJSON);

        return responseJSON;

    }

    /** 토큰 발급 요청 */
    private String getKakaoAccessToken(Map<String,String> reqParam){
        // 요청 URI
        String requestUri = "https://kauth.kakao.com/oauth/token";

//        요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        /* 요청 바디에 파라미터 설정 */
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",reqParam.get("appkey"));
        params.add("redirect_uri",reqParam.get("redirect"));
        params.add("code",reqParam.get("code"));

//        카카오 인증서버로 POST요청 날리기
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);
        /**
         *  -ResTemplate객체가 REST API 통신을 위한 API인데 (JS fetch역할)
         *  -서버에 통ㅅ니을 보내면서 응답을 받ㅇ르 수 있는 메서드 : exchange
         *
         *  param1 : 요청 URL
         *  param2 : 요청 방식 (get,post,put,patch,delete .. )
         *  param3 : 요청헤더와 바디 정보 - HttpEntity로 포장해서 보내야함
         *  param4 : 응답 결과(JSON)를 어떤 타입으로 받을 것인지 ( DTO, Map , ... )
          */

        ResponseEntity<Map> responseEntity = restTemplate.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);
//        응답 데이터에서 JSON 추출
        Map<String,Object> resJSON = (Map<String, Object>) responseEntity.getBody();
        log.debug("응답 데이터 : {}",resJSON);

//        access_token 추출
        String accessToken = (String) resJSON.get("access_token");
        return accessToken;
    }
}
