function NOxEmission = getNOxEmission(laneID)
%getNOxEmission Get the NOx emission in the lane.
%   NOxEmission = getNOxEmission(LANEID) Returns the NOx emission in mg for
%   the last time step on the given lane.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getNOxEmission.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
NOxEmission = traci.lane.getUniversal(constants.VAR_NOXEMISSION, laneID);