package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Contents {

    @Id
    @GeneratedValue
    @Column(name = "contents_id")
    private Long id;

    private String title;
    private String description;
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Contents createContents(String title, String description, String url, Member member) {
        Contents contents = new Contents();
        contents.setTitle(title);
        contents.setDescription(description);
        contents.setUrl(url);
        contents.setMember(member);

        return contents;
    }
}
