package itgo.it_secondhand.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {

    @Id @GeneratedValue
    @Column(name = "keyword_id")
    private Long id;

    private String keyword;
    private int count;

    private Keyword(String keyword){
        this.keyword = keyword;
        this.count = 0;
    }

    public int increaseCount(){
        this.count += 1;
        return count;
    }

    public static Keyword create(String keyword) {
        return new Keyword(keyword);
    }


}
