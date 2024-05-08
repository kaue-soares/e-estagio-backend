package kauesoares.eestagio.authservice.model;

import jakarta.persistence.*;
import kauesoares.eestagio.authservice.base.BaseEntity;
import kauesoares.eestagio.authservice.domain.HttpMethod;
import kauesoares.eestagio.authservice.domain.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "routes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Data
public class Route extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "private")
    private Boolean isPrivate;

    @Enumerated(EnumType.STRING)
    HttpMethod method;

    String path;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "routes_roles", joinColumns = @JoinColumn(name = "route_id"))
    @Column(name = "role_id")
    private Set<Role> allowedRoles = new HashSet<>();

    @OneToMany(
            mappedBy = "route",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    Set<RouteParam> params = new HashSet<>();

}
