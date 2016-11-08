function type = getType(poiID)
%getType Returns the (abstract) type of the poi.
%   type = getType(POIID) Returns the (abstract) type of the poi.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getType.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
type = traci.poi.getUniversal(constants.VAR_TYPE, poiID);