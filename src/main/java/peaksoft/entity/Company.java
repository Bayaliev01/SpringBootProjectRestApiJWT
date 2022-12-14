package peaksoft.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    @SequenceGenerator(name = "company_seq", sequenceName = "company_seq", allocationSize = 1)
    private Long id;

    //    @Size(min = 3, max = 20, message = "name of company should be between 3 and 20")
    @Column(length = 100000, name = "company_name")
    private String companyName;

    //    @NotEmpty(message = "country should not be empty")
    @Column(length = 100000, name = "located_country")
    private String locatedCountry;

    private int count;

    public void plusStudent(){
        count++;
    }
    public void minusStudent(){
        count--;
    }

    @OneToMany(cascade = {MERGE, REFRESH, DETACH, REMOVE, PERSIST}, fetch = LAZY, mappedBy = "company")
    private List<Course> courses;
    public void addCourse(Course course1){
        if (courses==null){
            courses=new ArrayList<>();
        }
        courses.add(course1);
    }

    @OneToMany(cascade = {MERGE, REFRESH, DETACH, REMOVE, PERSIST}, fetch = LAZY, mappedBy = "company")
    private List<Groups> groups;
    public void addGroup(Groups group){
        if (groups==null){
            groups=new ArrayList<>();
        }
        groups.add(group);
    }

}