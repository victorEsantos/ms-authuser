package com.ead.authuser.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilsService{

    @Value("${ead.api.url.course}")
    String courseRequestUrl;

    public String createUrl(UUID userId, Pageable pageable){
        return courseRequestUrl + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "sort=" + pageable.getSort().toString().replace(": ", ",");
    }
}
