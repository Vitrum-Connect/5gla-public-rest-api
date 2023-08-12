package de.app.fivegla.integration.agvolution;

public class AgvolutionSensorDataIntegrationServiceBuilder {
    private AccessTokenService accessTokenService;
    private AgvolutionSensorIntegrationService agvolutionSensorIntegrationService;

    public AgvolutionSensorDataIntegrationServiceBuilder setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
        return this;
    }

    public AgvolutionSensorDataIntegrationServiceBuilder setAgvolutionSensorIntegrationService(AgvolutionSensorIntegrationService agvolutionSensorIntegrationService) {
        this.agvolutionSensorIntegrationService = agvolutionSensorIntegrationService;
        return this;
    }

    public AgvolutionSensorDataIntegrationService createAgvolutionSensorDataIntegrationService() {
        return new AgvolutionSensorDataIntegrationService(accessTokenService, agvolutionSensorIntegrationService);
    }
}