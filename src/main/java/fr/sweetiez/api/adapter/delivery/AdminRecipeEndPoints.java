package fr.sweetiez.api.adapter.delivery;

import fr.sweetiez.api.core.products.models.requests.DeleteImageRequest;
import fr.sweetiez.api.core.recipes.models.recipes.Recipe;
import fr.sweetiez.api.core.recipes.models.requests.*;
import fr.sweetiez.api.core.recipes.models.responses.RecipeDetailedResponse;
import fr.sweetiez.api.core.recipes.services.RecipeService;
import fr.sweetiez.api.core.recipes.services.exceptions.InvalidRecipeException;
import fr.sweetiez.api.core.recipes.services.exceptions.RecipeNotFoundException;
import fr.sweetiez.api.core.recipes.services.exceptions.StepNotFoundException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public class AdminRecipeEndPoints {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;
    private final RecipeService recipeService;


    public AdminRecipeEndPoints(RecipeService recipeService, MinioClient minioClient) {
        this.recipeService = recipeService;
        this.minioClient = minioClient;
    }

    public ResponseEntity<RecipeDetailedResponse> create(CreateRecipeRequest request) {
        System.out.println(request);
        var recipe = recipeService.createRecipe(request);

        return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
    }

    public ResponseEntity<RecipeDetailedResponse> addStep(CreateStepRequest request) {
        var recipe = recipeService.addStep(request);

        return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
    }

    public ResponseEntity<List<RecipeDetailedResponse>> retrieveAll() {
        var recipes = recipeService.retrieveAll();

        return ResponseEntity.ok(recipes.recipes().stream()
                .map(RecipeDetailedResponse::new)
                .toList());
    }

    public ResponseEntity<RecipeDetailedResponse> retrieveById(String id) {
        try {
            var recipe = recipeService.retrieveById(id);
            return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
        } catch (InvalidRecipeException e) {
            return ResponseEntity.badRequest().build();
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<RecipeDetailedResponse> changeStepOrder(ChangeStepsOrderRequest request) {
        try {
            var recipe = recipeService.changeStepsOrder(request);
            return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
        } catch (InvalidRecipeException e) {
            return ResponseEntity.badRequest().build();
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<RecipeDetailedResponse> deleteStep(RemoveStepRequest request) {
        try {
            var recipe = recipeService.removeStep(request);
            return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
        } catch (InvalidRecipeException e) {
            return ResponseEntity.badRequest().build();
        } catch (RecipeNotFoundException | StepNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<RecipeDetailedResponse> addImage(String id, MultipartFile image) {
        var imageName = String.format("recipe_%s_%s", image.getOriginalFilename(), UUID.randomUUID());
        // Store the image in the minio bucket
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(imageName)
                    .stream(image.getInputStream(), image.getSize(), -1)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().header("Error", "Error while uploading file").build();
        }
        // Return the image URL
        String url;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.DELETE)
                            .bucket(bucketName)
                            .object(imageName)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().header("Error", "Error retrieving file").build();
        }
        url = url.substring(0, url.indexOf('?'));

        Recipe recipe;
        try {
            recipe = recipeService.addImage(id, url);
        } catch (RecipeNotFoundException | InvalidRecipeException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
    }

    public ResponseEntity<RecipeDetailedResponse> deleteImage(String id, DeleteImageRequest request) {
        try {
            var recipe = recipeService.deleteImage(id, request.imageUrl());
            return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidRecipeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<RecipeDetailedResponse> publish(PublishRecipeRequest request) {
        try {
            var recipe = recipeService.publish(request);
            return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidRecipeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<RecipeDetailedResponse> unPublish(UnPublishRecipeRequest request) {
        try {
            var recipe = recipeService.unPublish(request);
            return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidRecipeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<RecipeDetailedResponse> update(String id, UpdateRecipeRequest request) {
        try {
            var recipe = recipeService.update(id, request);
            return ResponseEntity.ok(new RecipeDetailedResponse(recipe));
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidRecipeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
