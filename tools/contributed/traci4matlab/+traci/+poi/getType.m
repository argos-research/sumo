function type = getType(poiID)
%getType Returns the (abstract) type of the poi.
%   type = getType(POIID) Returns the (abstract) type of the poi.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getType.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
type = traci.poi.getUniversal(constants.VAR_TYPE, poiID);