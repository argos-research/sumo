function minGap = getMinGap(personID)
%getMinGap Returns the gap to front person.
%   minGap = getMinGap(PERSONID) Returns the offset (gap to front person if 
%   halting) of this person.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getMinGap.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
minGap = traci.person.getUniversal(constants.VAR_MINGAP, personID);