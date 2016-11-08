function COEmission = getCOEmission(laneID)
%getCOEmission Returns the CO emission on the given lane.
%   COEmission = getCOEmission(LANEID) Returns the CO emission in mg for
%   the last time step on the given lane.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getCOEmission.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
COEmission = traci.lane.getUniversal(constants.VAR_COEMISSION, laneID);