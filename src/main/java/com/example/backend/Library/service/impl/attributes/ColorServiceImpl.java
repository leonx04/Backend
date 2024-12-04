package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.reponse.attributes.ColorResponse;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.entity.attributes.Color;
import com.example.backend.Library.model.mapper.attributes.ColorMapper;
import com.example.backend.Library.repository.attributes.color.ColorCustomizeQueryRepository;
import com.example.backend.Library.repository.attributes.color.ColorRepository;
import com.example.backend.Library.repository.products.productVariant.ProductVariantRepository;
import com.example.backend.Library.service.interfaces.attributes.ColorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class ColorServiceImpl implements ColorService {
     ColorRepository colorRepo;

     ProductVariantRepository variantRepo;

//    EmployeeRepo employeeRepo;

     ColorCustomizeQueryRepository colorCustomizeQueryRepository;
     ColorMapper colorMapper;

    @Override
    public PageableResponse getPageData(AttributeParamRequest paramRequest) {
        PageableResponse pageableResponse = colorCustomizeQueryRepository.getPageData(paramRequest);

        List<Color> colors = Optional.ofNullable(pageableResponse.getContent())
                .filter(content -> content instanceof List<?> && !((List<?>) content).isEmpty() && ((List<?>) content).get(0) instanceof Color)
                .map(content -> (List<Color>) content)
                .orElse(Collections.emptyList());

        List<ColorResponse> colorResponses  = mapAndEnhanceColorsToColorResponses(colors);//Map colors --> ColorResponse + bổ sung trường.
        pageableResponse.setContent(colorResponses );
        return pageableResponse;
    }

    @Override//lấy danh sách name trên 1 trang (pagination) dựa trên tiền tố.
    public PageableResponse getPageDataByPrefix(AttributeParamRequest paramRequest) {
        List<Color> colors =  colorCustomizeQueryRepository.getWithPagination(paramRequest);
        List<String> colorNames = colors.stream().map(Color::getName).toList();

        Long totalElements = colorCustomizeQueryRepository.getTotalElements(paramRequest);

        return PageableResponse.<String>builder()
                .totalElements(totalElements)
                .pageSize(paramRequest.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / paramRequest.getPageSize()))// tính tổng số trang = tổng số elements / kích thước 1 trang.
                .pageNo(paramRequest.getPageNo())
                .content(colorNames)
                .build();
    }

    @Override
    public PageableResponse getEntityByName(AttributeParamRequest paramRequest) {
        Optional<Color> color = colorRepo.findByName(paramRequest.getSearchByName());//entity || null.

        List<Color> colors = color.map(Collections::singletonList).orElse(Collections.emptyList());

        List<ColorResponse> content = mapAndEnhanceColorsToColorResponses(colors);
        return PageableResponse.builder()
                .totalElements(content.isEmpty() ? 0L : 1L)
                .pageSize(paramRequest.getPageSize())
                .totalPages(content.isEmpty() ? 0 : 1)
                .pageNo(paramRequest.getPageNo())//1
                .content(content)
                .build();
    }

    private List<ColorResponse> mapAndEnhanceColorsToColorResponses(List<Color> colors) {
        List<ColorResponse> colorResponses  = colors.stream()
                .map(color -> {
                    System.out.println( "hexColorCode: " + color.getHexColorCode());
                    ColorResponse colorResponse = colorMapper.toColorResponse(color);
                    colorResponse.setAppliedProductCount(variantRepo.countByColor(color.getId()));
                    return colorResponse;
                })
                .collect(Collectors.toList());
        return colorResponses ;
    }
}
