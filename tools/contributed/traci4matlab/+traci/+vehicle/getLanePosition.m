function lanePosition = getLanePosition(vehID)
%getLanePosition Get the position of the vehicle along the lane.
%   lanePosition = getLanePosition(VEHID) Returns the position of the 
%   vehicle along the lane measured in m.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLanePosition.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lanePosition = traci.vehicle.getUniversal(constants.VAR_LANEPOSITION, vehID);