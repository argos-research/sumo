function endingTeleportIDList = getEndingTeleportIDList()
%getEndingTeleportIDList Get the IDs of teleported vehicles.
%   endingTeleportIDList = getEndingTeleportIDList() Returns a cell array 
%   of strings containing the IDs of the vehicles which ended to be 
%   teleported in this time step. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getEndingTeleportIDList.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
endingTeleportIDList = traci.simulation.getUniversal(constants.VAR_TELEPORT_ENDING_VEHICLES_IDS);