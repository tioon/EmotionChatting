package chatting.domain.Room.controller;


import chatting.domain.Room.entity.Room;
import chatting.domain.Room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/chat")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/room")
    public String room(Model model){
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<Room> rooms(){
        return roomService.getChatRoomList();
    }

    /*@PostMapping("/room")
    @ResponseBody
    public Room createRoom(@RequestParam String name){
        return roomService.createChatRoom(name);
    }*/

    @PostMapping("/room/enter/{roomId}")
    public String enterRoom(Model model, @PathVariable String roomId, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("roomId", roomId);
        return "redirect:/chat/roomdetail";
    }
}
