package pt.amado.wisetax.base;

import pt.amado.wisetax.dto.ChargingRequestDTO;

public final class TestProvider {

    public static ChargingRequestDTO generateDummyChargingRequest() {
        ChargingRequestDTO dto = new ChargingRequestDTO();
        dto.setRsu(2);
        dto.setRoaming(true);
        dto.setService("A");
        dto.setPhoneNumber("+1234567890");
        return dto;
    }

}
