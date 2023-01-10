import com.gosecuri.agent.Agent;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class AgentTests {

    final String firstName;
    final String lastName;
    final String mission;
    final String password;
    final String kit;
    final String lampe;
    final String talky;

    public AgentTests() {
        firstName = "Corinne";
        lastName = "Berthier";
        mission = "Surveillance";
        password = "some_secure_password";
        kit = "kit";
        lampe = "lampe";
        talky = "talky";
    }

    @Test
    public void agent_username_is_first_letter_of_first_name_and_last_name_lowercase() throws NoSuchAlgorithmException {
        List<String> agentData = new ArrayList<>();
        agentData.add(lastName);
        agentData.add(firstName);
        agentData.add(mission);
        agentData.add(password);

        Agent agent = new Agent(agentData);
        String expected = (agent.getFirstName().charAt(0) + agent.getLastName()).toLowerCase(Locale.ROOT);

        assertEquals(expected, agent.getUsername());
    }

    @Test
    public void create_agent_with_missing_information_throws_exception() throws NoSuchAlgorithmException {
        List<String> agentData = new ArrayList<>();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Agent agent = new Agent(agentData);
        });
    }

    @Test
    public void create_agent_without_equipment() throws NoSuchAlgorithmException {
        List<String> agentData = new ArrayList<>();
        agentData.add(lastName);
        agentData.add(firstName);
        agentData.add(mission);
        agentData.add(password);

        Agent agent = new Agent(agentData);

        assertEquals(lastName, agent.getLastName());
        assertEquals(firstName, agent.getFirstName());
        assertEquals(mission, agent.getMission());
        assertNotEquals(password, agent.getEncryptedPassword());
        assertEquals(0, agent.getEquipment().size());
    }

    @Test
    public void create_agent_with_equipment_returns_size_3() throws NoSuchAlgorithmException {
        List<String> agentData = new ArrayList<>();
        agentData.add(lastName);
        agentData.add(firstName);
        agentData.add(mission);
        agentData.add(password);
        agentData.add(""); //separates agent data from equipment
        agentData.add(kit);
        agentData.add(lampe);
        agentData.add(talky);

        Agent agent = new Agent(agentData);

        assertEquals(lastName, agent.getLastName());
        assertEquals(firstName, agent.getFirstName());
        assertEquals(mission, agent.getMission());
        assertNotEquals(password, agent.getEncryptedPassword());
        assertEquals(3, agent.getEquipment().size());
    }
}
