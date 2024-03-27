package chatting.domain.Room.service;

import chatting.domain.Room.entity.Room;
import chatting.domain.Room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;


    // 방생성
    /*public Room createChatRoom(String name) {
        return roomRepository.save(new RoomEnterRequest(UUID.randomUUID().toString(),name).of());
    }

    // 방입장
    public Room enterChatRoom(RoomEnterRequest roomEnterRequest) {
        return roomRepository.save(roomEnterRequest.of());
    }*/

    public List<Room> getChatRoomList(){
        return roomRepository.findAll();
    }

}