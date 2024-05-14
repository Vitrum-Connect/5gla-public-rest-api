package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Response;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.CreateGroupRequest;
import de.app.fivegla.controller.dto.response.CreateGroupResponse;
import de.app.fivegla.controller.dto.response.ReadGroupResponse;
import de.app.fivegla.controller.dto.response.ReadGroupsResponse;
import de.app.fivegla.controller.dto.response.UpdateGroupResponse;
import de.app.fivegla.persistence.entity.Group;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.GROUPS)
public class GroupController {

    private final GroupService groupService;

    @Operation(
            operationId = "groups.create",
            description = "Creates a new group.",
            tags = BaseMappings.GROUPS
    )
    @ApiResponse(
            responseCode = "201",
            description = "The group was created successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateGroupResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> createGroup(@Valid @RequestBody CreateGroupRequest createGroupRequest) {
        var createdGroup = groupService.add(Group.from(createGroupRequest));
        var createGroupResponse = CreateGroupResponse.builder()
                .groupId(createdGroup.getGroupId())
                .name(createdGroup.getName())
                .description(createdGroup.getDescription())
                .createdAt(createdGroup.getCreatedAt().toString())
                .updatedAt(createdGroup.getUpdatedAt().toString())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createGroupResponse);
    }

    @Operation(
            operationId = "groups.read",
            description = "Reads an existing group.",
            tags = BaseMappings.GROUPS
    )
    @ApiResponse(
            responseCode = "200",
            description = "The group was read successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ReadGroupResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @GetMapping(value = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> readGroup(@PathVariable("groupId") @Parameter(description = "The unique ID of the group.") String groupId) {
        var optionalGroup = groupService.get(groupId);
        if (optionalGroup.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response());
        } else {
            var group = optionalGroup.get();
            var readGroupResponse = ReadGroupResponse.builder()
                    .groupId(group.getGroupId())
                    .name(group.getName())
                    .description(group.getDescription())
                    .createdAt(group.getCreatedAt().toString())
                    .updatedAt(group.getUpdatedAt().toString())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(readGroupResponse);
        }
    }

    @Operation(
            operationId = "groups.read-all",
            description = "Reads all existing groups.",
            tags = BaseMappings.GROUPS
    )
    @ApiResponse(
            responseCode = "200",
            description = "All groups were read successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ReadGroupsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> readAllGroups() {
        var groups = groupService.getAll();
        var readGroupsResponse = ReadGroupsResponse.builder()
                .groups(groups.stream()
                        .map(group -> de.app.fivegla.controller.dto.response.inner.Group.builder()
                                .groupId(group.getGroupId())
                                .name(group.getName())
                                .description(group.getDescription())
                                .createdAt(group.getCreatedAt().toString())
                                .updatedAt(group.getUpdatedAt().toString())
                                .build())
                        .toList())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(readGroupsResponse);
    }

    @Operation(
            operationId = "groups.update",
            description = "Updates an existing group.",
            tags = BaseMappings.GROUPS
    )
    @ApiResponse(
            responseCode = "200",
            description = "The group was updated successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UpdateGroupResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> updateGroup() {
        var updateGroupResponse = UpdateGroupResponse.builder().build();
        return ResponseEntity.status(HttpStatus.OK).body(updateGroupResponse);
    }

    @Operation(
            operationId = "groups.delete",
            description = "Deletes an existing group.",
            tags = BaseMappings.GROUPS
    )
    @ApiResponse(
            responseCode = "200",
            description = "The group was deleted successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @DeleteMapping(value = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> deleteGroup(@PathVariable("groupId") @Parameter(description = "The unique ID of the group.") String groupId) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response());
    }

}
