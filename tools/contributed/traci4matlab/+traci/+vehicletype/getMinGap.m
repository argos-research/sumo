function minGap = getMinGap(typeID)
%getMinGap Returns the offset of vehicles of this type.
%   minGap = getMinGap(TYPEID) Returns the offset (gap to front vehicle if 
%   halting) of vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getMinGap.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
minGap = traci.vehicletype.getUniversal(constants.VAR_MINGAP, typeID);