function roadID = getRoadID(vehID)
%getRoadID Returns the edge the vehicle was at in last step. 
%   roadID = getRoadID(VEHID) Returns the id of the edge the named vehicle 
%   was at within the last step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
roadID = traci.vehicle.getUniversal(constants.VAR_ROAD_ID, vehID);