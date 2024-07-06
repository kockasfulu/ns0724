package com.primetoolrentals.tooltrek_api.controller.rental;

import com.primetoolrentals.tooltrek_api.dto.ToolDto;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class ToolsDto {

    private ToolDto chns;
    private ToolDto ladw;
    private ToolDto jakd;
    private ToolDto jakr;
}
