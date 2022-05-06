package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimaryTorpedoStore;
  private TorpedoStore mockSecondaryTorpedoStore; 

  @BeforeEach
  public void init(){
    mockPrimaryTorpedoStore = mock(TorpedoStore.class);
    mockSecondaryTorpedoStore = mock(TorpedoStore.class); 

    this.ship = new GT4500(mockPrimaryTorpedoStore, mockSecondaryTorpedoStore);
    
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore, times(1)).fire(1); 
    verify(mockSecondaryTorpedoStore, times(0)).fire(1); 
  
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore, times(1)).fire(1); 
    verify(mockSecondaryTorpedoStore, times(1)).fire(1); 

  }

  @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true); 
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryTorpedoStore, times(0)).fire(0); 
  
  }

  @Test
  public void fireTorpedo_Single_Twice_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false); 

    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    boolean both = result&&result2;  

    // Assert
    assertEquals(true, both);
    verify(mockPrimaryTorpedoStore, times(1)).fire(1); 
    verify(mockSecondaryTorpedoStore, times(1)).fire(1); 
  
  }

  @Test
  public void fireTorpedo_Single_Twice_EmptySecondary_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false); 

    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    boolean both = result&&result2;  

    // Assert
    assertEquals(true, both);
    verify(mockPrimaryTorpedoStore, times(2)).fire(1); 
    verify(mockSecondaryTorpedoStore, times(0)).fire(1); 
  
  }

}
