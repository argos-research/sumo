function endingTeleportNumber = getEndingTeleportNumber()
%getEndingTeleportNumber Get the number teleported vehicles.
%   endingTeleportNumber = getEndingTeleportNumber() Returns the number of 
%   vehicles which ended to be teleported in this time step.  

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getEndingTeleportNumber.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
endingTeleportNumber = traci.simulation.getUniversal(constants.VAR_TELEPORT_ENDING_VEHICLES_NUMBER);