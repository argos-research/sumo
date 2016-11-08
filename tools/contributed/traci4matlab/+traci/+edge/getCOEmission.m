function COEmission = getCOEmission(edgeID)
%getCOEmission Returns the CO emission on the given edge.
%   COEmission = getCOEmission(EDGEID) Returns the CO emission in mg for 
%   the last time step on the given edge.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getCOEmission.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
COEmission = traci.edge.getUniversal(constants.VAR_COEMISSION, edgeID);