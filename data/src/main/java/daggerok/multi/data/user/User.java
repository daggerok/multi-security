package daggerok.multi.data.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@ToString(exclude = "password")
@EqualsAndHashCode(exclude = "id")
@RequiredArgsConstructor(staticName = "of")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    @Id @GeneratedValue Long id;
    @NonNull String username;
    @NonNull String password;
}