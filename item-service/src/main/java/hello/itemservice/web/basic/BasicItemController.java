package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam int quantity,
                       Model model) {
        Item item = new Item(itemName, price, quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("Item") Item item, Model model) {

        itemRepository.save(item);
//        model.addAttribute("item", item); -> 위에 @ModelAttribute("Item") 이 자동으로
//        model.addAttribute("item", item) 를 수행해줌 @ModelAttribute("Item") 내의 "Item" 가
//        model attribute 의 키임
//        @ModelAttribute("Hello") Item item -> model.addAttribute("Hello", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model) {

        itemRepository.save(item);
//        클래스명의 첫 글자만 소문자로 바꿔서 model.addAttribute("item", item) 를 수행해줌

        return "basic/item";
    }

//    @PostMapping("/add")
//    @ModelAttribute 쓰면 model 생략 가능
    public String addItemV4(@ModelAttribute Item item) {

        itemRepository.save(item);

        return "basic/item";
    }

//    @PostMapping("/add")
//    매개변수로 객체를 받으면 @ModelAttribute 생략 가능
    public String addItemV5(Item item) {

        itemRepository.save(item);

        return "basic/item";
    }

//    @PostMapping("/add")
//    PRG 패턴
    public String addItemV6(Item item) {

        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
        //만약 뒤에 붙는 문자가 한글이거나 띄어쓰기가 있거나 하면 이렇게 쓰면 안됨
    }

    @PostMapping("/add")
    public String addItemV7(Item item, RedirectAttributes redirectAttributes) {

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
        /*
        localhost:8080/basic/items/5?status=true
        url 에 안들어간 attribute 는 쿼리 파라미터로 들어감
         */
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

//        return "redirect:/basic/items/"  + item.getId();
        return "redirect:/basic/items/{itemId}";// @PathVariable 썼으면 RedirectAttribute 쓸 필요 없음
    }

    /*
    테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("ItemB", 20000, 20));
    }
}
