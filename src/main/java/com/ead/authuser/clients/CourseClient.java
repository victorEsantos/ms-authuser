package com.ead.authuser.clients;

import com.ead.authuser.user.api.dtos.CourseDto;
import com.ead.authuser.user.api.dtos.ResponsePageDto;
import com.ead.authuser.util.UtilServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilServiceImpl utilService;

    @Value("${ead.api.url.course}")
    String courseRequestUrl;

    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable) {
        List<CourseDto> searchResult = null;
        String url = utilService.createUrl(userId, pageable);

        try {
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {
            };
            ResponseEntity<ResponsePageDto<CourseDto>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();

        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {}", e);
        }

        return new PageImpl<>(searchResult);
    }

    public void deleteUserInCourse(UUID userId){
        String url = courseRequestUrl + "/courses/users/" + userId;

        restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
