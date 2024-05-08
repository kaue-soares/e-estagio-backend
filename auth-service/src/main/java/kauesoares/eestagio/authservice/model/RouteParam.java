package kauesoares.eestagio.authservice.model;

import jakarta.persistence.*;
import kauesoares.eestagio.authservice.base.BaseEntity;
import kauesoares.eestagio.authservice.domain.PathParamType;
import lombok.*;

@Entity
@Table(name = "route_params")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Data
public class RouteParam extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    @Enumerated(EnumType.STRING)
    PathParamType type;

    @ManyToOne
    @JoinColumn(name = "route_id")
    Route route;

}
