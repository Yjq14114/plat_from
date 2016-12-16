package tt.wifi.app.tree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import tt.wifi.app.hotel.model.Hotel;
import tt.wifi.app.hotel.service.iface.IHotelService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangjq on 2016/10/19.
 */
@Controller
@RequestMapping("/tree")
public class TreeController {
    @Resource
    private IHotelService hotelService;
    @RequestMapping(value = "/getTree",produces = {"application/json;charset=UTF-8"},method={RequestMethod.GET})
    public ModelAndView getTree(){
        Hotel hotel = hotelService.hotel(14);
        Map<String,Object> map = new HashMap<>();
        map.put("id",1);
        map.put("text",hotel.getName());
        map.put("state","closed");
        map.put("pid",0);
        return new ModelAndView(new MappingJacksonJsonView(),map);
    }
}
