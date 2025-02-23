package com.pigierbackend.etablissementsource;

import java.util.List;

public interface EtabSourceService {
EtabSourceResponseDto creatOrUpdateEtabSource(EtabSourceRequestDto dto);
Boolean deleteEtabSource(Long id);
List<EtabSourceResponseDto>findAllEtabSour();
}
