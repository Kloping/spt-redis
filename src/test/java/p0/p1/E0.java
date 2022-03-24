package p0.p1;

import java.util.Objects;

/**
 * @author github.kloping
 */
public class E0 {
    private String name = "1";
    private Integer age = 2;
    private Boolean sex = false;

    public E0() {
    }

    public E0(Integer a) {
        name = this.getName() + a;
        age = a * age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        E0 e0 = (E0) o;
        return Objects.equals(name, e0.name) && Objects.equals(age, e0.age) && Objects.equals(sex, e0.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex);
    }
}
