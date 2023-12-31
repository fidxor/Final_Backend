package project.lincook.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.lincook.backend.common.exception.ErrorCode;
import project.lincook.backend.common.exception.LincookAppException;
import project.lincook.backend.entity.*;
import project.lincook.backend.repository.ContentsRepository;
import project.lincook.backend.repository.DetailContentRepository;
import project.lincook.backend.repository.MemberRepository;
import project.lincook.backend.repository.ProductRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentsService {

    private final ContentsRepository contentsRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final DetailContentRepository detailContentRepository;

    @Transactional
    //컨텐트 등록
    public Long addContents(Long memberId, String title, String description, String url) {
        // 멤버엔티티 조회
        Member member = memberRepository.findOne(memberId);

        if (member == null) {
            throw new LincookAppException(ErrorCode.NON_EXISTENT_MEMBER, String.format("memberId :", memberId));
        }

        // 등록된 영상인지 확인.
        validateDuplicateContents(url);
        // 컨텐츠 생성
        Contents contents = Contents.createContents(title, description, url, member);

        // 컨텐츠 저장
        contentsRepository.save(contents);

        return contents.getId();
    }

    private void validateDuplicateContents(String url) {
        List<Contents> contentsList = contentsRepository.findByUrl(url);

        if (!contentsList.isEmpty()) {
            throw new LincookAppException(ErrorCode.DUPLICATED_CONTENTS_URL, String.format("url :", url));
        }
    }

    @Transactional
    // 상세 컨텐츠 정보 등록
    public Long addDetailContent(Long contentsId, Long productId) {
        // 엔티티 조회
        Contents contents = contentsRepository.findOne(contentsId);
        Product product = productRepository.findOne(productId);

        DetailContent detailContent = new DetailContent();

        DetailContentProduct dcp = DetailContentProduct.createDetailContentProduct(detailContent, product);

        detailContent.addProduct(dcp);
        detailContent.setContents(contents);

        detailContentRepository.save(detailContent);

        return detailContent.getId();
    }
}
