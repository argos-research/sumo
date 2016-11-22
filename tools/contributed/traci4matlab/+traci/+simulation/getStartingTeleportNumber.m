function startingTeleportNumber = getStartingTeleportNumber()
%getStartingTeleportNumber Get the number of vehicles stsrting to teleport.
%   startingTeleportNumber = getStartingTeleportNumber() Returns the number
%   of vehicles which started to teleport in this time step.  

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getStartingTeleportNumber.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
startingTeleportNumber = traci.simulation.getUniversal(constants.VAR_TELEPORT_STARTING_VEHICLES_NUMBER);