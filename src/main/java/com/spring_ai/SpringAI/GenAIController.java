package com.spring_ai.SpringAI;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GenAIController {

    private ChatService chatService;

    private ReceipieService receipieService;
    private ImageService imageService;

    public GenAIController(ChatService chatService, ImageService imageService, ReceipieService receipieService) {
        this.chatService = chatService;
        this.receipieService = receipieService;
        this.imageService = imageService;
    }

    @GetMapping("/ask-ai")
    public String getResponse(@RequestParam String prompt){
        return chatService.getResponse(prompt);
    }

    @GetMapping("/ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt){
        return chatService.getResponseOptions(prompt);
    }

    @GetMapping("/generate-image")
    public void getResponseImage(HttpServletResponse response, @RequestParam String prompt) throws IOException {
        ImageResponse imageResponse = imageService.generateImage(prompt);
        String imageURL = imageResponse.getResult().getOutput().getUrl();
        response.sendRedirect(imageURL);
    }

    @GetMapping("/recipe-create")
    public String recipeCreator(@RequestParam String ingredients,
                                      @RequestParam(defaultValue = "any") String cuisine,
                                      @RequestParam(defaultValue = "") String dietaryRestrictions){

        return receipieService.createRecipe(ingredients,cuisine, dietaryRestrictions);
    }
}
