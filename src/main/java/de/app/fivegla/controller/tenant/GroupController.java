package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Response;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.CreateGroupRequest;
import de.app.fivegla.controller.dto.request.UpdateGroupRequest;
import de.app.fivegla.controller.dto.response.*;
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

import java.security.Principal;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.GROUPS)
public class GroupController implements TenantCredentialApiAccess {

    private final GroupService groupService;
    private final TenantService tenantService;

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
    public ResponseEntity<? extends Response> createGroup(@Valid @RequestBody CreateGroupRequest createGroupRequest, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        var createdGroup = groupService.add(tenant, Group.from(createGroupRequest));
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
    public ResponseEntity<? extends Response> readGroup(@PathVariable("groupId") @Parameter(description = "The unique ID of the group.") String groupId, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        var optionalGroup = groupService.get(tenant, groupId);
        if (optionalGroup.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response());
        }
        var group = optionalGroup.get();
        var readGroupResponse = ReadGroupResponse.builder()
                .groupId(group.getGroupId())
                .name(group.getName())
                .description(group.getDescription())
                .createdAt(group.getCreatedAt().toString())
                .updatedAt(group.getUpdatedAt().toString())
                .sensorIdsAssignedToGroup(group.getSensorIdsAssignedToGroup())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(readGroupResponse);
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
    public ResponseEntity<? extends Response> readAllGroups(Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        var groups = groupService.getAll(tenant);
        var readGroupsResponse = ReadGroupsResponse.builder()
                .groups(groups.stream()
                        .map(group -> de.app.fivegla.controller.dto.response.inner.Group.builder()
                                .groupId(group.getGroupId())
                                .name(group.getName())
                                .description(group.getDescription())
                                .createdAt(group.getCreatedAt().toString())
                                .updatedAt(group.getUpdatedAt().toString())
                                .sensorIdsAssignedToGroup(group.getSensorIdsAssignedToGroup())
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
    public ResponseEntity<? extends Response> updateGroup(@Valid @RequestBody UpdateGroupRequest updateGroupRequest, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        var optionalGroup = groupService.update(tenant, Group.from(updateGroupRequest));
        if (optionalGroup.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response());
        }
        var updateGroupResponse = UpdateGroupResponse.builder()
                .groupId(optionalGroup.get().getGroupId())
                .name(optionalGroup.get().getName())
                .description(optionalGroup.get().getDescription())
                .createdAt(optionalGroup.get().getCreatedAt().toString())
                .updatedAt(optionalGroup.get().getUpdatedAt().toString())
                .build();
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
    public ResponseEntity<? extends Response> deleteGroup(@PathVariable("groupId") @Parameter(description = "The unique ID of the group.") String groupId, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        var optionalGroup = groupService.get(tenant, groupId);
        if (optionalGroup.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response());
        }
        groupService.delete(tenant, groupId);
        return ResponseEntity.status(HttpStatus.OK).body(new Response());
    }

    @Operation(
            operationId = "groups.assign-sensor",
            description = "Assigns a sensor to an existing group.",
            tags = BaseMappings.GROUPS
    )
    @ApiResponse(
            responseCode = "200",
            description = "The sensor was assigned to the group successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SensorAddedToGroupResponse.class)
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
    @PostMapping(value = "/{groupId}/assign-sensor/{sensorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> assignSensorToExistingGroup(@PathVariable("groupId") @Parameter(description = "The unique ID of the group.") String groupId,
                                                                          @PathVariable("sensorId") @Parameter(description = "The unique ID of the sensor.") String sensorId,
                                                                          Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        Optional<Group> group = groupService.assignSensorToExistingGroup(tenant, groupId, sensorId);
        if (group.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response());
        }
        return ResponseEntity.status(HttpStatus.OK).body(SensorAddedToGroupResponse.builder()
                .groupId(group.get().getGroupId())
                .name(group.get().getName())
                .description(group.get().getDescription())
                .createdAt(group.get().getCreatedAt().toString())
                .updatedAt(group.get().getUpdatedAt().toString())
                .sensorIdsAssignedToGroup(group.get().getSensorIdsAssignedToGroup())
                .build()
        );
    }

}
