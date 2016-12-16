package tt.wifi.app.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tt.wifi.app.hotel.model.Hotel;
import tt.wifi.app.hotel.service.iface.IHotelService;

import javax.annotation.Resource;

/**
 * Created by yangjq on 2016/9/9.
 */
@Controller
@RequestMapping("/hotel")
public class HotelController{
    @Resource
    private IHotelService hotelService;
    @RequestMapping(value = "/getHotel",params = "hotelId")
    public void getHotel(long hotelId){
        Hotel hotel = hotelService.hotel(hotelId);
        System.out.println(hotel.getAddress());
    }
}
