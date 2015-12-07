function startingTeleportIDList = getStartingTeleportIDList()
%getStartingTeleportIDList Get the IDs of vehicles stsrting to teleport.
%   startingTeleportIDList = getStartingTeleportIDList() Returns a cell 
%   array of strings containing the ids of vehicles which which started to 
%   teleport in this time step.  

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
startingTeleportIDList = traci.simulation.getUniversal(constants.VAR_TELEPORT_STARTING_VEHICLES_IDS);