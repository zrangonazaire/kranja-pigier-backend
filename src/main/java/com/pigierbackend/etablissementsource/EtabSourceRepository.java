package com.pigierbackend.etablissementsource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;
@ResponseBody
public interface EtabSourceRepository extends JpaRepository<EtabSource,Long> {

}
