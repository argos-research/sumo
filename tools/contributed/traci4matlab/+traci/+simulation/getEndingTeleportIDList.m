function endingTeleportIDList = getEndingTeleportIDList()
%getEndingTeleportIDList Get the IDs of teleported vehicles.
%   endingTeleportIDList = getEndingTeleportIDList() Returns a cell array 
%   of strings containing the IDs of the vehicles which ended to be 
%   teleported in this time step. 

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
endingTeleportIDList = traci.simulation.getUniversal(constants.VAR_TELEPORT_ENDING_VEHICLES_IDS);