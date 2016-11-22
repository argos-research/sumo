function startingTeleportIDList = getStartingTeleportIDList()
%getStartingTeleportIDList Get the IDs of vehicles stsrting to teleport.
%   startingTeleportIDList = getStartingTeleportIDList() Returns a cell 
%   array of strings containing the ids of vehicles which which started to 
%   teleport in this time step.  

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getStartingTeleportIDList.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
startingTeleportIDList = traci.simulation.getUniversal(constants.VAR_TELEPORT_STARTING_VEHICLES_IDS);