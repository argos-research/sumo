function traveltime = getTraveltime(laneID)
%getTraveltime Get estimated travel time in the lane.
%   traveltime = getTraveltime(LANEID) Returns the estimated travel time in
%   seconds for the last time step on the given lane.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getTraveltime.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traveltime = traci.lane.getUniversal(constants.VAR_CURRENT_TRAVELTIME, laneID);