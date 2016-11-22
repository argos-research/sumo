function type = getType(polygonID)
%getType Returns the (abstract) type of the polygon.
%   type = getType(POLYGONID) Returns the (abstract) type of the polygon.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getType.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
type = traci.polygon.getUniversal(constants.VAR_TYPE, polygonID);