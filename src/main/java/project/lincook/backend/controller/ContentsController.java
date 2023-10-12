package project.lincook.backend.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.lincook.backend.common.exception.ErrorCode;
import project.lincook.backend.common.exception.LincookAppException;
import project.lincook.backend.dto.ContentsDto;
import project.lincook.backend.dto.Response;
import project.lincook.backend.entity.Contents;
import project.lincook.backend.entity.Member;
import project.lincook.backend.repository.ContentsRepository;
import project.lincook.backend.service.ContentsService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;
    private final ContentsRepository contentsRepository;

    /**
     * 메인페이지 컨텐츠 목록 가져오기
     * @param request
     * @return
     */
    @GetMapping("/main")
    public Response findContents(findContentsRequest request) {
        List<Contents> contentsList = contentsRepository.findAll(request.offset, request.limit);

        List<ContentsDto> result = contentsList.stream()
                .map(c -> new ContentsDto(c.getId(), c.getMember().getId(), c.getTitle(), c.getDescription(), c.getUrl()))
                .collect(Collectors.toList());

        return Response.success(result);
    }


    /**
     * Contents 등록
     * @param request
     * @return
     */
    @PostMapping("/create-contents")
    public Response createContents(@RequestBody CreateContentsRequest request) {
        // 재료 상품 List가 비어있으면 에러.
        if (request.ids.isEmpty()) {
            throw new LincookAppException(ErrorCode.EMPTY_PRODUCT_LIST, String.format("url : ", request.url));
        }

        // url string이 비어있으면 에러
        if (request.url.isEmpty()) {
            throw new LincookAppException(ErrorCode.CONTENTS_EMPTY_URL, String.format("title : ", request.title));
        }

        Long contentId = contentsService.addContents(request.member_id, request.title, request.description, request.url);

        for (Long id : request.ids) {
            contentsService.addDetailContent(contentId, id);
        }

        return Response.success(new CreateContentsResponse(contentId));
    }

    @Data
    static class CreateContentsResponse {
        private Long id;

        public CreateContentsResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateContentsRequest {

        private Long member_id;
        private String title;
        private String description;
        private String url;

        private List<Long> ids = new ArrayList<>();
    }

    @Data
    static class findContentsRequest {

        private int offset;
        private int limit;
    }
}
