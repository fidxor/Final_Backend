package project.lincook.backend.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    public Response findContents(@RequestBody findContentsRequest request) {
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
    public CreateContentsResponse createContents(@RequestBody CreateContentsRequest request) {

        Long contentId = contentsService.addContents(request.member_id, request.title, request.description, request.url);

        for (Long id : request.ids) {
            contentsService.addDetailContent(contentId, request.name, id);
        }

        return new CreateContentsResponse(contentId);
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

        private String name;
        private List<Long> ids = new ArrayList<>();
    }

    @Data
    static class findContentsRequest {

        private int offset;
        private int limit;
    }
}