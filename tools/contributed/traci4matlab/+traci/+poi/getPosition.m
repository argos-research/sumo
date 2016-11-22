function position = getPosition(poiID)
%getPosition Get the position of the poi.
%   position = getPosition(POIID) Returns the position coordinates of 
%   the given poi. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getPosition.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
position = traci.poi.getUniversal(constants.VAR_POSITION, poiID);