function lastStepOccupancy = getLastStepOccupancy(edgeID)
%getLastStepOccupancy Get the percentage of occupation on the edge.
%   lastStepOccupancy = getLastStepOccupancy(EDGEID) Returns the occupancy 
%   in percentage for the last time step on the given edge.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepOccupancy.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
lastStepOccupancy = traci.edge.getUniversal(constants.LAST_STEP_OCCUPANCY, edgeID);