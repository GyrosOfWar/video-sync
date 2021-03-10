package xyz.tomasi.videosync.repository;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;
import xyz.tomasi.videosync.dto.ParticipantDto;
import xyz.tomasi.videosync.dto.RoomDto;
import xyz.tomasi.videosync.dto.VideoDto;
import xyz.tomasi.videosync.entity.Room;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

public class RoomRepositoryImpl implements RoomRepositoryCustom {
  public RoomRepositoryImpl(R2dbcEntityTemplate template) {
    this.template = template;
  }
  private final R2dbcEntityTemplate template;

  @Override
  public Mono<RoomDto> findRoom(long id) {
    String query = """
      SELECT r.id, r.name, r.created_at, r.active_video,
      p.id, p.name, p.created_at,
      v.id, v.added_at, v.added_by, v.url
      FROM room r 
      INNER JOIN participant p ON r.id = p.room_id 
      INNER JOIN video v ON r.id = v.room_id
      WHERE r.id = :id
      """;

    return template.getDatabaseClient()
      .sql(query)
      .bind("id", id)
      .map(r -> r)
      .all()
      .collectList()
      .flatMap(rows -> {
        if (rows.isEmpty()) {
          return Mono.empty();
        } else {

          var participants = rows.stream()
            .map(row -> new ParticipantDto(
              row.get(4, Long.class),
              row.get(5, String.class),
              row.get(6, ZonedDateTime.class)
            ))
            .collect(Collectors.toList());
          var videos = rows.stream()
            .map(row -> new VideoDto(
              row.get(7, Long.class),
              row.get(9, ZonedDateTime.class),
              row.get(10, Long.class),
              URI.create(row.get(11, String.class))
            ))
            .collect(Collectors.toList());
          var firstRow = rows.get(0);

          return Mono.just(new RoomDto(
            firstRow.get(0, Long.class),
            firstRow.get(1, String.class),
            firstRow.get(2, ZonedDateTime.class),
            participants,
            videos
          ));
        }
      });
  }
}
