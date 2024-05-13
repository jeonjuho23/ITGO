package itgo.it_secondhand.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CategoryBySite {

    @Id @GeneratedValue
    @Column(name = "category_id_by_site")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String siteName;
    private String categoryBySite;

    protected CategoryBySite(){}

    private CategoryBySite(Category category, String siteName, String categoryBySite){
        this.category = category;
        this.siteName = siteName;
        this.categoryBySite = categoryBySite;
    }

    public static CategoryBySite createCategoryBySite(Category category, String siteName, String categoryBySite){
        return new CategoryBySite(category, siteName, categoryBySite);
    }

}
