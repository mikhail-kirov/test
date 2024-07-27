package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bookings", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "bookerId")
    private Long bookerId;

    @Column(name = "startTime")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    private LocalDateTime endTime;

    @Value("${some.key:0}")
    @Column(name = "status")
    private Integer status;
}